<?php

namespace App\Controller;

use App\Entity\Pist;
use App\Form\PistType;
use App\Repository\PistRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/pist")
 */
class PistController extends AbstractController
{
    /**
     * @Route("/", name="pist_index", methods={"GET"})
     */
    public function index(PistRepository $pistRepository): Response
    {
        return $this->render('pist/index.html.twig', [
            'pists' => $pistRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="pist_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $pist = new Pist();
        $form = $this->createForm(PistType::class, $pist);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($pist);
            $entityManager->flush();

            return $this->redirectToRoute('pist_index');
        }

        return $this->render('pist/new.html.twig', [
            'pist' => $pist,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="pist_show", methods={"GET"})
     */
    public function show(Pist $pist): Response
    {
        return $this->render('pist/show.html.twig', [
            'pist' => $pist,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="pist_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Pist $pist): Response
    {
        $form = $this->createForm(PistType::class, $pist);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('pist_index');
        }

        return $this->render('pist/edit.html.twig', [
            'pist' => $pist,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="pist_delete", methods={"POST"})
     */
    public function delete(Request $request, Pist $pist): Response
    {
        if ($this->isCsrfTokenValid('delete'.$pist->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($pist);
            $entityManager->flush();
        }

        return $this->redirectToRoute('pist_index');
    }

    /**
     * @Route("pist/recherche",name="recherche")
     */
    function Recherche(PistRepository $repository, Request $request){
        $data=$request->get('search');
        $pist=$repository->findBy(['nom'=>$data]);
        return $this->render('pist/index.html.twig', [
            'pists' => $pist]);

    }
}
